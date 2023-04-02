const admin = require("firebase-admin");
const functions = require("firebase-functions");
admin.initializeApp();
const token1 =
       "eiYYUPsvTwy78vu3tfXA3E:APA91bH4pGZCPRPjRNMZ0Z"+
       "_0hNN8uwUM4Ii6rhHM5xCiPMeQzT2SJAkdff95pN-goscP"+
       "_UZe2kYSUowHZzX3SmGHIVBoWG8g8hn16U0suRMtIEGPqLYqbi_"+
       "D6EXoVklkFe7nviHz7dPh";
console.log(token1);

const message = {
  notification: {
    title: "New message!",
    body: "You have a new message from Firebase Cloud Messaging!",
  },
  token: token1,
};
console.log("created");

module.exports = functions.database.ref("/cases")
    .onCreate((snapshot, context) => {
      console.log("onCreate");
      // send notification here
      admin.messaging().send(message)
          .then((response) => {
            console.log("Successfully sent message:", response);
          })
          .catch((error) => {
            console.error("Error sending message:", error);
          });
    });
console.log("Done");
