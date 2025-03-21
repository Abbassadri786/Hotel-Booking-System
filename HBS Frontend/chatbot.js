// Step 1
// Generate a JWT token

// {
//         "org_id": "13627",
//         "chatbot_id": "67c73f4c8a6ff31f9818e693",
//         "user_id":  "13446",
//         "variables": {
//             // Add your variables here: "key": "value"
//         }
//     }

// Access Key: sa5c4OyYFTkPSb

// Step 2
// Add below code in your product.

// <script 
//  id= "chatbot-main-script"
//  embedToken="Enter Embed Token here"
//  src="https://chatbot-embed.viasocket.com/chatbot-prod.js"
// </script>

// Usage
// Use this methods to receive data.

// window.addEventListener('message', (event) => {
//         const receivedData = event.data;
//      });

// Available functions
// Use this methods to interact with chatbot

// 1. Use This method to send data when needed
// window.SendDataToChatbot({ 
//       bridgeName: 'React Frontend',
//       threadId: 'React_Frontend',
//       parentId: '<parent_container_id>',
//       fullScreen: 'false',
//       hideCloseButton: 'false',
//       hideIcon: 'false',
//       variables: {}
//     });

// Variables, you can pass to the chatbot using SendDataToChatbot method.
// Parameter	Type	Description	Required
// bridgeName	string	The slug name of the bridge.	true
// threadId	string	The ID corresponding to the chat store.	true
// parentId	string	The parent container ID in which you want to open chatbot.	false
// fullScreen	boolean	Whether to open the chatbot in full screen.	false
// hideCloseButton	boolean	Whether to hide the close button.	false
// hideIcon	boolean	Whether to hide the icon.	false
// variables	object	Additional variables for the chatbot.

// 2. Use this method to open chatbot explicitly
// window.openChatbot()
// 3. Use this method to close chatbot explicitly
// window.closeChatbot()

// import { useEffect, useState } from 'react';
// import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
// import "react-bootstrap";
// import "bootstrap";
// import './index.css';

// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

// import Home from './compnents/home/Home';
// import EditRoom from './compnents/room/EditRoom';
// import ExistingRooms from './compnents/room/ExistingRooms';
// import AddRoom from './compnents/room/AddRoom';
// import NavBar from './compnents/layout/NavBar';
// import Footer from './compnents/layout/Footer';
// // import RoomFilter from './compnents/common/RoomFilter';
// import RoomListing from './compnents/room/RoomListing';
// import Admin from './compnents/admin/Admin';
// import Checkout from './compnents/bookings/Checkout';
// import BookingSuccess from './compnents/bookings/BookingSuccess';
// import Bookings from './compnents/bookings/Bookings';
// import FindBooking from './compnents/bookings/FindBooking';
// import Login from './compnents/auth/Login'; // Import the Login component
// import Register from './compnents/auth/Register';
// import FeedbackForm from './compnents/feedback/FeedbackForm';

// function App() {
//     const scriptId = "chatbot-main-script";
//     const scriptSrc = "https://chatbot-embed.viasocket.com/chatbot-prod.js";
//     const [scriptLoaded, setScriptLoaded] = useState(false);

//     useEffect(() => {
//         const existingScript = document.getElementById(scriptId);
//         if (existingScript) {
//             document.head.removeChild(existingScript);
//         }
        
//         const script = document.createElement("script");
//         script.setAttribute("embedToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdfaWQiOiIxMzYyNyIsImNoYXRib3RfaWQiOiI2N2M3M2Y0YzhhNmZmMzFmOTgxOGU2OTMiLCJ1c2VyX2lkIjoiMTM2MjcifQ.1-9xRjQhkegm1JYaCZIuBsn9jFmNR7KbORl-riMW5P4");
//         script.setAttribute("hideIcon", "true");
//         script.id = scriptId;
//         script.src = scriptSrc;
//         script.onload = () => setScriptLoaded(true);
//         document.head.appendChild(script);
//     }, []);

//     useEffect(() => {
//         if (!scriptLoaded) return;

//         const intervalId = setInterval(() => {
//             window.SendDataToChatbot({
//                 bridgeName: "React Frontend",
//                 threadId: "React_Frontend",
//                 parentId: 'parentChatbot',
//                 fullScreen: false,
//                 hideCloseButton: true,
//                 hideIcon: true,
//                 version_id: "67c73f039a2faeb85554120c"
//             });

//             setTimeout(() => {
//                 if (window?.openChatbot) {
//                     window.openChatbot();
//                 }
//             }, 100);

            
//         }, 300);

//         return () => {
//             // clearInterval(intervalId);
//         };
//     }, [scriptLoaded]);
//     return (
//         <>
//             <Router>
//                 <NavBar />
//                 <Routes>
//                     {/* Default path set to Login */}
//                     <Route path="/" element={<Login />} />
//                     <Route path="/home" element={<Home />} />
//                     <Route path="/existing-rooms" element={<ExistingRooms />} />
//                     <Route path="/edit-room/:id" element={<EditRoom />} />
//                     <Route path="/add-room" element={<AddRoom />} />
//                     <Route path="/browse-all-rooms" element={<RoomListing />} />
//                     <Route path="/admin" element={<Admin />} />
//                     <Route path="/book-room/:id" element={<Checkout />} />
//                     <Route path="/feedback" element={<FeedbackForm />} />
//                     <Route path="/booking-success" element={<BookingSuccess />} />
//                     <Route path="/existing-bookings" element={<Bookings />} />
//                     <Route path="/find-booking" element={<FindBooking />} />
//                     <Route path="/register" element={<Register />} />
//                     <Route path="/login" element={<Login />} />
//                 </Routes>
//             </Router>
//             <Footer />
//         </>
//     );
// }

// export default App;
