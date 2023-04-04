
const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();
const db = admin.firestore();
exports.sendCaseNotification = functions.firestore
    .document("cases/")
    .onWrite(async (change, context) => {
      const caseData = change.after.data();
      const previousCaseData = change.before.data();
      const caseId = context.params.caseId;

      // Get list of users who are subscribed to notifications for this case
      const tokens = [];
      db.collection("tokens").get().then((querySnapshot) => {
        querySnapshot.forEach((doc) => {
          // Retrieve token value
          const token = doc.data().token;
          // Use token for notification sending
          tokens.push(token);
        });
      }).catch((error) => {
        console.log("Error getting tokens: ", error);
      });

      // Check if case status has changed
      if (caseData.status !== previousCaseData.status) {
        // Send notification to all subscribed users
        const payload = {
          notification: {
            title: `Case ${caseId} updated`,
            body: `Status: ${caseData.status}`,
            clickAction: "FLUTTER_NOTIFICATION_CLICK",
          },
        };

        const options = {
          priority: "high",
          timeToLive: 60 * 60 * 24,
        };

        await admin.messaging().sendToDevice(tokens, payload, options);
      }
    });
