import { useEffect, useState } from "react";
import "./OrderHistory.css";
import { latestOrders } from "../../../service/orderService";

const OrderHistory = () => {

    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await latestOrders();
                setOrders(response.data);
            } catch (error) {
                console.error(error);
            }finally{
                setLoading(false);
            }
        }
        fetchOrders();
    },[]);

    

    return(
        <div>Order History</div>
    )
}

export default OrderHistory;