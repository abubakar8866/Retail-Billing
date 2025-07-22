import { useContext, useState } from 'react';
import './Explore.css';
import { AppContext } from '../../context/AppContext';
import  DisplayCategory  from "../../components/DisplayCategory/DisplayCategory";
import DisplayItems from "../../components/DisplayItems/DisplayItems";
import CartItems from '../../components/CartItems/CartItems';
import CartSummary from "../../components/CartSummary/CartSummary";
import CutomerForm from "../../components/CustomerForm/CustomerForm";

const Explore = () => {
    
    const {categories} = useContext(AppContext);
    const [selectedCategory,SetselectedCategory] = useState("");

    return (
        <div className="explore-container text-light">
            <div className="left-column">
                <div className="first-row" style={{overflowY:'auto'}}>
                    <DisplayCategory 
                        selectedCategory={selectedCategory}
                        SetselectedCategory={SetselectedCategory}
                        categories={categories}                         
                    />
                </div>
                <hr className="horizontal-line"/>
                <div className="second-row" style={{overflowY:'auto'}}>
                    <DisplayItems></DisplayItems>
                </div>
            </div>
            <div className="right-column d-flex flex-column">
                <div className="customer-form-container" style={{height:'15%'}}>
                    <CutomerForm></CutomerForm>
                </div>
                <hr className="my-3 text-light"/>
                <div className="cart-items-container" style={{height:'55%',overflowY:'auto'}}>
                    <CartItems></CartItems>
                </div>
                <div className="cart-summary-container" style={{height:'30%'}}>
                    <CartSummary></CartSummary>
                </div>
            </div>
        </div>
    );
}

export default Explore;