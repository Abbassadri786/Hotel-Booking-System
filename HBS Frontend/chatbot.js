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

// const scriptId = "chatbot-main-script";
//   const scriptSrc = "https://chatbot-embed.viasocket.com/chatbot-prod.js";
//      useEffect(() => {
//     const updateScript = () => {
//       const existingScript = document.getElementById(scriptId);
//       if (existingScript) {
//         document.head.removeChild(existingScript);
//       }
//       if (true) {
//         const script = document.createElement("script");
//         script.setAttribute("embedToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdfaWQiOiIxMzYyNyIsImNoYXRib3RfaWQiOiI2N2M3M2Y0YzhhNmZmMzFmOTgxOGU2OTMiLCJ1c2VyX2lkIjoiMTM0NDYifQ.qPWLBJzLlfhHflJBpxtGMNyEZ0iJ0NuXLMpuM8lE9ZE");
//         script.setAttribute("hideIcon", true);
//         script.id = scriptId;
//         script.src = scriptSrc;
//         document.head.appendChild(script);
//       }
//     };
//     setTimeout(() => {
//       updateScript();
//     }, 150); // Delay of 150ms
  
//     return () => {
//       const existingScript = document.getElementById(scriptId);
//       if (existingScript) {
//         document.head.removeChild(existingScript);
//       }
//     };
//   }, []);
//      useEffect(() => {
//     const intervalId = setInterval(() => {
//       if (window?.SendDataToChatbot && window.openChatbot && document.getElementById('parentChatbot') ) {
//         window.SendDataToChatbot({
//           "bridgeName": "React Frontend",
//           "threadId": "React_Frontend",
//           "parentId": 'parentChatbot',
//           "fullScreen": true,
//           "hideCloseButton": true,
//           "hideIcon": true,
//           "version_id":  "67c73f039a2faeb85554120c"
//         })
// setTimeout(() => {
//           if (bridgeTypeRef.current === 'chatbot') window?.openChatbot();
//         }, 200);
//         clearInterval(intervalId);
//       }
//     }, 300);
//     return () => {
//       clearInterval(intervalId);
//       // if (typeof window.closeChatbot === "function") {
//       //     SendDataToChatbot({
//       //         parentId: '',
//       //         fullScreen: false,
//       //     });
//       //     setTimeout(() => {
//       //         closeChatbot();
//       //     }, 0);
//       // }
//     };
//   }, []);
