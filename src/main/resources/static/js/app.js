var LogDataList = App.Components.LogDataList;

var LogDataBox = React.createClass({
    getInitialState: function() {
        return {
            logData: []
        };
    },
    fetchCompanies: function () {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({logData: data.logData});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    componentDidMount: function () {
        this.fetchCompanies();
    },
    render() {
        return (
            <LogDataList logData={this.state.logData}/>
        );
    }
});

// var App = React.createClass({
//     fetchCompanies: function () {
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
//     componentDidMount: function() {
//         this.fetchCompanies();
//     },
//     render: function() {
//         var i;
//         var logDataNodes = [];
//
//         for (i = 0; i < this.props.logData.length; i++) {
//             logDataNodes.push(
//                 <LogDataListRow logdata={this.props.logData[i]} />
//             );
//         }
//
//         return (
//             <div className="logDataList">
//                 <LogDataList companies={this.state.logData}/>
//                 {logDataNodes}
//             </div>
//         );
//     }
// });

// var App = React.createClass({
//     render: function() {
//         var i;
//         var logDataNodes = [];
//
//         for (i = 0; i < this.props.logData.length; i++) {
//             logDataNodes.push(
//                 <LogDataList logdata={this.props.logData[i]} />
//             );
//         }
//
//         return (
//             <div className="logDataList">
//                 {logDataNodes}
//             </div>
//         );
//     }
// });

// App.Components.LogDataList = React.createClass({
//     render: function() {
//         var i;
//         var logDataNodes = [];
//
//         for (i = 0; i < this.props.logData.length; i++) {
//             logDataNodes.push(
//                 <LogDataList logdata={this.props.logData[i]} />
//             );
//         }
//
//         return (
//             <div className="logDataList">
//                 {logDataNodes}
//             </div>
//         );
//     }
// });

// class LogDataListRow extends React.Component {
//     verbLevel(props) {
//         return (
//             <div className="panel panel-success">
//                 <div className="panel-heading">Verb</div>
//                 <div className="panel-body">
//                     <p>Time : {props.logData.time}</p>
//                     <p>Tag : {props.logData.tag}</p>
//                     <p>Message : {props.logData.message}</p>
//                     <p>Level : {props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
//
//     debugLevel(props) {
//         return (
//             <div className="panel panel-primary">
//                 <div className="panel-heading">Debug</div>
//                 <div className="panel-body">
//                     <p>Time : {props.logData.time}</p>
//                     <p>Tag : {props.logData.tag}</p>
//                     <p>Message : {props.logData.message}</p>
//                     <p>Level : {props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
//
//     informationLevel(props) {
//         return (
//             <div className="panel panel-info">
//                 <div className="panel-heading">Info</div>
//                 <div className="panel-body">
//                     <p>Time : {props.logData.time}</p>
//                     <p>Tag : {props.logData.tag}</p>
//                     <p>Message : {props.logData.message}</p>
//                     <p>Level : {props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
//
//     errorLevel(props) {
//         return (
//             <div className="panel panel-danger">
//                 <div className="panel-heading">Error</div>
//                 <div className="panel-body">
//                     <p>Time : {props.logData.time}</p>
//                     <p>Tag : {props.logData.tag}</p>
//                     <p>Message : {props.logData.message}</p>
//                     <p>Level : {props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
//
//     warningLevel(props) {
//         return (
//             <div className="panel panel-warning">
//                 <div className="panel-heading">Warning</div>
//                 <div className="panel-body">
//                     <p>Time : {props.logData.time}</p>
//                     <p>Tag : {props.logData.tag}</p>
//                     <p>Message : {props.logData.message}</p>
//                     <p>Level : {props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
//
//     render() {
//         return (
//             <div className="panel panel-success">
//                 <div className="panel-heading">Verb</div>
//                 <div className="panel-body">
//                     <p>Time : {this.props.logData.time}</p>
//                     <p>Tag : {this.props.logData.tag}</p>
//                     <p>Message : {this.props.logData.message}</p>
//                     <p>Level : {this.props.logData.level}</p>
//                 </div>
//             </div>
//         );
//     }
// }
//
// class LogDataList extends React.Component {
//     render() {
//         var i;
//         var logDataNodes = [];
//
//         console.log(this.props);
//         console.log(this.state);
//         for (i = 0; i < this.props.logData.length; i++) {
//             logDataNodes.push(
//                 <LogDataListRow logdata={this.state.logData[i]}/>
//             );
//         }
//
//         return (
//             <div className="logDataList">
//                 {logDataNodes}
//             </div>
//         );
//     }
// }
//
// class LogDataView extends React.Component {
//     constructor(props) {
//         super(props);
//
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
//     }
//
//     render() {
//         return (
//             <LogDataList logData={this.state.logData}/>
//         );
//     }
// }
//
// class App extends React.Component {
//     render() {
//         return (
//             <LogDataView url="/logdatalist"/>
//         )
//
//     }
// }

ReactDOM.render(<LogDataBox url="/logdatalist"/>, document.getElementById('root'));