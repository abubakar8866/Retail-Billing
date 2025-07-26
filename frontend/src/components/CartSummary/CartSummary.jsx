import { useContext, useState } from "react";
import "./CartSummary.css";
import { AppContext } from "../../context/AppContext";
import {createOrder, deleteOrder} from "../../../service/orderService";
import {toast} from "react-hot-toast";
import {createRazorpayOrder, verifyPayment} from "../../../service/paymentService";
import { AppConstants } from "../../util/constants";
//import ReceiptPopup from "../ReceiptPopup/ReceiptPopup";


const CartSummary = ({customerName,mobileNumber,setMobileNumer,setCustomerName}) => {

    const {cartItems,clearCart} = useContext(AppContext);
    const [isProcessing,setIsProcessing] = useState(false);
    const [orderDetails,setOrderDetails] = useState(null);
    const totalAmount = cartItems.reduce((total, item) => total + item.price * item.quantity,0);
    const tax = totalAmount * 0.01;
    const grandTotal = totalAmount + tax;

    const clearAll = () => {
        setCustomerName("");
        setMobileNumer("");
        clearCart();
        
    }
    
    const loadRazorpayScript = () => {
        return new Promise((resolve,reject)=>{
            const script = document.createElement('script');
            script.src = "https://checkout.razorpay.com/v1/checkout.js";
            script.onload = () => resolve(true);
            script.onload = () => resolve(false);
            document.body.appendChild(script);
        });
    }

    const deleteOrderOnFailure = async (orderId) => {
        try {
            await deleteOrder(orderId);
        } catch (error) {
            console.error(error);
            toast.error("Something went wrong!");
        }
    }

    const completePayment = async (paymentMode) => {
        if(!customerName || !mobileNumber){
            toast.error("Please enter customer details.");
            return;
        }

        if (cartItems.length === 0) {
            toast.error("Your cart is empty.");
            return;
        }

        const orderData = {
            customerName,
            phoneNumber: mobileNumber,
            cartItems,
            subtotal: totalAmount,
            tax,
            grandTotal,
            paymentMode: paymentMode.toUpperCase()
        }

        setIsProcessing(true);
        try {
            const response = await createOrder(orderData);
            const saveData = response.data;
            if (response.status === 201 && paymentMode === "cash") {
                toast.success("Cash received");
                setOrderDetails(saveData);
            }else if(response.status === 201 && paymentMode === "upi"){
                const razorpayLoaded = await loadRazorpayScript();
                if (!razorpayLoaded) {
                    toast.error('Unable to load razorpay.');
                    await deleteOrderOnFailure(saveData.orderId);
                    return;
                }

                const razorpayResponse = await createRazorpayOrder({amount: grandTotal,currency: 'INR'});
                const options = {
                    key:AppConstants.RAZORPAY_KEY_ID,
                    amount:razorpayResponse.data.amount,
                    currency:razorpayResponse.data.currency,
                    order_id:razorpayResponse.data.id,
                    name:"My Retail Shop",
                    description:"Order Payment",
                    handler:async (response) => {
                        await verifyPaymentHandler(response);
                    },
                    prefill:{
                        name:customerName,
                        contact:mobileNumber,
                    },
                    theme:{
                        color:"#3399cc"
                    },
                    modal:{
                        ondismiss: async () => {
                            deleteOrderOnFailure(saveData.orderId);
                            toast.error("Payment cancelled");
                        }
                    },
                };
                const rzp = new window.Razorpay(options);
                rzp.on("payment failed",async (response) => {
                    await deleteOrderOnFailure(saveData.orderId);
                    toast.error("Payment Failed");
                    console.error(response.error.description);
                });
                rzp.open()
            }
        } catch (error) {
            console.error(error);
            toast.error("Payment processing failed");
        }finally{
            setIsProcessing(false);
        }
    }

    const verifyPaymentHandler  = async (response, savedOrder) => {
        const paymentData = {
            razorpayOrderId: response.razorpay_order_id,
            razorpayPaymentId: response.razorpay_payment_id,
            razorpaySignature: response.razorpay_signature,
            orderId: savedOrder.orderId
        };
        try {
            const paymentResponse = verifyPayment(paymentData);
            if (paymentResponse.status === 200) {
                toast.success("Payment Successful...");
                setOrderDetails({
                    ...savedOrder,
                    paymentDetails:{
                        razorpayOrderId: response.razorpay_order_id,
                        razorpayPaymentId: response.razorpay_payment_id,
                        razorpaySignature: response.razorpay_signature
                    },
                });
            }else{
                toast.error("Payment processing failed");
            }
        } catch (error) {
            console.error(error);
            toast.error("Payment failed");
        }
    };

    return (
        <div className="mt-1">

            <div className="cart-summary-details">
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Item: </span>
                    <span className="text-light">₹{totalAmount.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Tax (1%):</span>
                    <span className="text-light">₹{tax.toFixed(2)}</span>
                </div>
                <div className="d-flex justify-content-between mb-2">
                    <span className="text-light">Total:</span>
                    <span className="text-light">₹{grandTotal.toFixed(2)}</span>
                </div>
            </div>

            <div className="d-flex gap-3">
                <button className="btn btn-success flex-grow-1">Cash</button>
                <button className="btn btn-primary flex-grow-1">UPI</button>
            </div>

            <div className="d-flex gap-1 mt-1">
                <button className="btn bnt-warning flex-grow-1">
                    Place Order
                </button>
            </div>

        </div>
    );
}

export default CartSummary;