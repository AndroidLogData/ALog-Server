// import React from 'react';
// import ReactDOM from 'react-dom';
// import $ from "jquery";
// import {BrowserRouter as Router, Route, Link} from 'react-router-dom'
//
// class CrashSideMenu extends React.Component {
//     constructor(props) {
//         super(props);
//
//         this.state = {
//             logData: []
//         };
//
//         this.fetchPackageName();
//     }
//
//     fetchPackageName() {
//         $.ajax({
//             url: '/packagenamedatalist',
//             dataType: "json",
//             cache: false,
//             success: function (data) {
//                 this.setState({logData: data.logData});
//             }.bind(this),
//             error: function (data) {
//                 console.log("error");
//             }.bind(this)
//         });
//     }
//
//     shouldComponentUpdate(nextProps, nextState) {
//         this.fetchPackageName();
//         return nextState.length !== this.state.logData.length;
//     }
//
//     render() {
//         let i;
//         let packageNameNode = [];
//
//         for (i = 0; i < this.state.logData.length; i++) {
//             packageNameNode.push(
//                 <li className="nav-item">
//                     <Link className="nav-link"
//                           to={{pathname: '/crashpackagenamefilter/' + this.state.logData[i]}}>{this.state.logData[i]}</Link>
//                 </li>
//             );
//         }
//
//         if (packageNameNode.length === 0) {
//             return (
//                 <div className="container-fluid">
//                     <div className="row">
//                         <nav className="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
//                             <ul className="nav nav-pills flex-column">
//                             </ul>
//                         </nav>
//                     </div>
//                 </div>
//             );
//         } else {
//             return (
//                 <div className="container-fluid">
//                     <div className="row">
//                         <nav className="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
//                             <ul className="nav nav-pills flex-column">
//                                 {packageNameNode}
//                             </ul>
//                         </nav>
//                     </div>
//                 </div>
//             );
//         }
//     }
// }
//
// const rootElement = document.getElementById('sidemenu');
// ReactDOM.render(
//     <Router>
//         <div>
//             <CrashSideMenu/>
//             <Route path="/crashpackagenamefilter/:packagename"/>
//         </div>
//     </Router>, rootElement);