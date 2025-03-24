import { useEffect, useState } from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "react-bootstrap";
import "bootstrap";
import "./index.css";

import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Home from "./compnents/home/Home";
import EditRoom from "./compnents/room/EditRoom";
import ExistingRooms from "./compnents/room/ExistingRooms";
import AddRoom from "./compnents/room/AddRoom";
import NavBar from "./compnents/layout/NavBar";
import Footer from "./compnents/layout/Footer";
import RoomListing from "./compnents/room/RoomListing";
import Admin from "./compnents/admin/Admin";
import Checkout from "./compnents/bookings/Checkout";
import BookingSuccess from "./compnents/bookings/BookingSuccess";
import Bookings from "./compnents/bookings/Bookings";
import FindBooking from "./compnents/bookings/FindBooking";
import Login from "./compnents/auth/Login";
import Register from "./compnents/auth/Register";
import FeedbackForm from "./compnents/feedback/FeedbackForm";
import ExistingCustomers from "./compnents/customer/ExistingCustomers";
import EditCustomer from "./compnents/customer/EditCustomer";
import AboutUs from "./compnents/layout/AboutUs";
import PrivacyPolicy from "./compnents/layout/PrivacyPolicy";

function App() {
    const scriptId = "chatbot-main-script";
    const scriptSrc = "https://chatbot-embed.viasocket.com/chatbot-prod.js";
    const [scriptLoaded, setScriptLoaded] = useState(false);
    const [chatbotVisible, setChatbotVisible] = useState(false);

    useEffect(() => {
        const existingScript = document.getElementById(scriptId);
        if (existingScript) {
            document.head.removeChild(existingScript);
        }

        const script = document.createElement("script");
        script.setAttribute(
            "embedToken",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdfaWQiOiIxMzYyNyIsImNoYXRib3RfaWQiOiI2N2M3M2Y0YzhhNmZmMzFmOTgxOGU2OTMiLCJ1c2VyX2lkIjoiMTM2MjcifQ.1-9xRjQhkegm1JYaCZIuBsn9jFmNR7KbORl-riMW5P4"
        );
        script.setAttribute("hideIcon", "true");
        script.setAttribute("hideCloseButton", "true");
        script.id = scriptId;
        script.src = scriptSrc;
        script.onload = () => setScriptLoaded(true);
        document.head.appendChild(script);
    }, []);

    useEffect(() => {
        if (!scriptLoaded) return;

        const intervalId = setInterval(() => {
            window.SendDataToChatbot({
                bridgeName: "React Frontend",
                threadId: "React_Frontend",
                parentId: 'parentChatbot',
                fullScreen: false,
                hideCloseButton: true,
                hideIcon: true,
                version_id: "67c73f039a2faeb85554120c"
            });
        }, 300);

        return () => {
            clearInterval(intervalId);
        };
    }, [scriptLoaded]);

    const toggleChatbot = () => {
        if (chatbotVisible) {
            if (window?.closeChatbot) window.closeChatbot();
        } else {
            if (window?.openChatbot) window.openChatbot();
        }
        setChatbotVisible(!chatbotVisible);
    };

    return (
        <>
            <div className="text-center my-3">
                <button
                    className="btn btn-primary"
                    onClick={toggleChatbot}
                >
                    {chatbotVisible ? "Close AI Chatbot" : "Ask AI"}
                </button>
            </div>
            <Router>
                <NavBar />
                <Routes>
                    <Route path="/" element={<Login />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/existing-rooms" element={<ExistingRooms />} />
                    <Route path="/edit-room/:id" element={<EditRoom />} />
                    <Route path="/add-room" element={<AddRoom />} />
                    <Route path="/browse-all-rooms" element={<RoomListing />} />
                    <Route path="/admin" element={<Admin />} />
                    <Route path="/book-room/:id" element={<Checkout />} />
                    <Route path="/feedback" element={<FeedbackForm />} />
                    <Route path="/booking-success" element={<BookingSuccess />} />
                    <Route path="/existing-bookings" element={<Bookings />} />
                    <Route path="/find-booking" element={<FindBooking />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/existing-customers" element={<ExistingCustomers />} />
                    <Route path="/edit-customer/:id" element={<EditCustomer />} />
                    <Route path="/about-us" element={<AboutUs />} />
                    <Route path="/privacy-policy" element={<PrivacyPolicy />} />
                </Routes>
            </Router>
            <Footer />
        </>
    );
}

export default App;
