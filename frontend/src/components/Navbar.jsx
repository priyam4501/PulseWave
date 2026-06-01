import { Link, useNavigate } from "react-router-dom";

import { useAuth } from "../context/useAuth";

function Navbar() {

    const navigate = useNavigate();

    const { logout } = useAuth();

    const handleLogout = () => {

        logout();

        navigate("/login");
    };

    return (

        <nav>

            <Link to="/dashboard">
                Dashboard
            </Link>

            {" | "}

            <Link to="/chat">
                Chat
            </Link>

            {" | "}

            <button
                onClick={handleLogout}
            >
                Logout
            </button>

        </nav>
    );
}

export default Navbar;