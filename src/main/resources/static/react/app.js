import React from 'react';

// var LogDataList = App.Components.LogDataList;

// var LogDataBox = React.createClass({
//     getInitialState: function () {
//         return {
//             logData: []
//         };
//     },
//     fetchLogData: function () {
//         $.ajax({
//             url: this.props.url,
//             dataType: 'json',
//             cache: false,
//             success: function (data) {
//                 this.setState({logData: data.logData});
//             }.bind(this),
//             error: function (xhr, status, err) {
//                 console.error(this.props.url, status, err.toString());
//             }.bind(this)
//         });
//     },
//     componentDidMount: function () {
//         this.fetchLogData();
//     },
//     shouldComponentUpdate: function () {
//         this.fetchLogData();
//         return true;
//     },
//     componentWillReceiveProps: function () {
//         this.fetchLogData();
//     },
//     render() {
//         return (
//             <LogDataList logData={this.state.logData}/>
//         );
//     }
// });

class LogDataBox extends React.Component {
    render() {
        return (
            <div>
                <h1>Hello, World!</h1>
                <h2>Hello, World!</h2>
            </div>
        );
    }
}

class App extends React.Component {
    render() {
        return (
            <div>
                <LogDataBox/>
            </div>
        );
    }
}

export default App;

// ReactDOM.render(<LogDataBox url="/logdatalist"/>, document.getElementById('root'));

// ReactDOM.render(<Router history={browserHistory}>
//     <LogDataBox path='/logdata' component={LogDataBox} url="/logdatalist"/>
// </Router>, document.getElementById('root'));