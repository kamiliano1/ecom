import { useContext, useState, useEffect } from "react";
import "./App.css";
import { AuthContext } from "react-oauth2-code-pkce";

function App() {
  const [message, setMessage] = useState("0");
  const { token, tokenData, logIn, logOut, isAuthenticated } =
    useContext(AuthContext);
  useEffect(() => {
    if (token) {
      console.log("Token: ", token);
    }
  }, [token, isAuthenticated]);

  const fetchHello = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/home", {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "text/plain; charset=utf-8",
        },
      });
      if (response.ok) {
        const data = await response.text();
        setMessage(data);
        console.log("Response from /api/home:", data);
      }
    } catch (error) {
      console.error("Error fetching home:", error);
      setMessage("Error fetching home");
    }
  };
  return (
    <div className="section">
      <h1>OAuth2 PKCE Demo</h1>
      <div>
        {!token ? (
          <button onClick={() => logIn()}>Log In</button>
        ) : (
          <div className="token-info">
            <h3>Message from API: </h3>
            <p>{message}</p>
            <button onClick={() => logOut()}>Log Out</button>
            {/* <p>Token: {token}</p> */}
            <button onClick={fetchHello}>Fetch /api/home</button>
            {message}
            <pre>Token Data: {JSON.stringify(tokenData, null, 2)}</pre>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
