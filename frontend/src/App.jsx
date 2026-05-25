import { useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";

function App() {

  const stompClientRef = useRef(null);

  const [sender, setSender] = useState("");
  const [receiver, setReceiver] = useState("");
  const [message, setMessage] = useState("");

  const [messages, setMessages] = useState([]);

  useEffect(() => {

    const client = new Client({

      brokerURL: "ws://localhost:8084/ws",

      debug: (str) => {
        console.log(str);
      },

      reconnectDelay: 5000,

      onConnect: () => {

        console.log("Connected to WebSocket");

        client.subscribe(
          "/topic/messages",
          (payload) => {

            const receivedMessage =
              JSON.parse(payload.body);

            console.log(receivedMessage);

            setMessages((prev) => [
              ...prev,
              receivedMessage
            ]);
          }
        );
      },

      onStompError: (frame) => {

        console.error(
          "Broker error:",
          frame.headers["message"]
        );
      },

      onWebSocketError: (error) => {

        console.error(
          "WebSocket Error:",
          error
        );
      }
    });

    client.activate();

    stompClientRef.current = client;

    return () => {

      if (stompClientRef.current) {
        stompClientRef.current.deactivate();
      }
    };

  }, []);

  const sendMessage = () => {

    if (!stompClientRef.current) {
      return;
    }

    const chatMessage = {
      sender,
      receiver,
      content: message
    };

    stompClientRef.current.publish({

      destination: "/app/chat",

      body: JSON.stringify(chatMessage)
    });

    setMessage("");
  };

  return (

    <div style={{ padding: "20px" }}>

      <h1>PulseWave Chat</h1>

      <input
        type="text"
        placeholder="Sender"
        value={sender}
        onChange={(e) =>
          setSender(e.target.value)
        }
      />

      <br /><br />

      <input
        type="text"
        placeholder="Receiver"
        value={receiver}
        onChange={(e) =>
          setReceiver(e.target.value)
        }
      />

      <br /><br />

      <input
        type="text"
        placeholder="Message"
        value={message}
        onChange={(e) =>
          setMessage(e.target.value)
        }
      />

      <br /><br />

      <button onClick={sendMessage}>
        Send
      </button>

      <hr />

      <h2>Messages</h2>

      {

        messages.map((msg, index) => (

          <div key={index}>

            <strong>
              {msg.sender}
            </strong>

            : {msg.content}

          </div>
        ))
      }

    </div>
  );
}

export default App;