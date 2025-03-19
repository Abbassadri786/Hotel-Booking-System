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