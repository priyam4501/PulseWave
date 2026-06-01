import { Link } from "react-router-dom";

function DashboardPage() {

    return (

        <div>

            <h1>Dashboard</h1>

            <Link to="/chat">
                Go To Chat
            </Link>

        </div>
    );
}

export default DashboardPage;