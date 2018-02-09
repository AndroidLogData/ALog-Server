import React from 'react';

class Navigation extends React.Component {
    render() {
        return (
            <nav className="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
                <button className="navbar-toggler navbar-toggler-right hidden-lg-up" type="button" data-toggle="collapse"
                        data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"/>
                </button>
                <a className="navbar-brand" href="#">Dashboard</a>

                <div className="collapse navbar-collapse justify-content-md-end" id="navbarsExampleDefault">
                    <ul className="navbar-nav">
                        <li className="nav-item"><a className="nav-link" href="/">Dash Board</a></li>
                        <li className="nav-item"><a className="nav-link" href="/logdata">Log Data</a></li>
                        <li className="nav-item"><a className="nav-link" href="/help">Help</a></li>
                        <li sec:authorize="isAnonymous()" className="nav-item"><a className="nav-link" href="/login">Login</a></li>
                        <li sec:authorize="isAnonymous()" className="nav-item"><a className="nav-link" href="/registration">Registration</a></li>
                        <li sec:authorize="isAuthenticated()" className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" sec:authentication="name"/>
                            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="dropdown03">
                                <a className="dropdown-item" href="/mypage" th:text="'My Page'"/>
                                <div className="dropdown-divider"></div>
                                <a className="dropdown-item" href="/logout" th:text="Logout"/>
                            </div>
                        </li>
                    </ul>
                    <!--<form class="form-inline mt-2 mt-md-0">-->
                    <!--<input class="form-control mr-sm-2" type="text" placeholder="Search"/>-->
                    <!--<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>-->
                    <!--</form>-->
                </div>
            </nav>
        );
    }
}

export default Navigation;