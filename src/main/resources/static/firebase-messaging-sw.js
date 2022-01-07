importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

// Initialize Firebase
var config = {
    apiKey: "AIzaSyBSIcxioJ725DeZRsSTHN03iH3xFMNez54",
    authDomain: "sosofcm-700ef.firebaseapp.com",
    projectId: "sosofcm-700ef",
    storageBucket: "sosofcm-700ef.appspot.com",
    messagingSenderId: "704008753036",
    appId: "1:704008753036:web:3a97d8a2a458990f4450a9",
    measurementId: "G-056G62GWG9"
};

firebase.initializeApp(config);

const messaging = firebase.messaging();

// messaging.getToken({vapidKey: "BDa8mrH4-G0UOp-XTaEhy2fRpdKjKlKmW26y3lWaHecuQpQ9_iGdUex7JrsL8VBxzMphaDeguYfHq1-WDqIkjes"});
// messaging.setBackgroundMessageHandler(function (payload) {
//     console.log('[firebase-messaging-sw.js] Received background message  [set]', payload);
//     // Customize notification here
//     const notificationTitle = payload.data.notification.title;
//     const notificationOptions = {
//         body: payload.data.notification.body,
//     };
//
//     self.registration.showNotification(notificationTitle,
//         notificationOptions);
// });

// messaging.onBackgroundMessage((payload) => {
//     console.log('[firebase-messaging-sw.js] Received background message [on]', payload);
//     // Customize notification here
//     const notificationTitle = payload.notification.title;
//     const notificationOptions = {
//         body: payload.notification.body,
//     };
//
//     return self.registration.showNotification(notificationTitle,
//         notificationOptions);
// });
