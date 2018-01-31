(function () {
    window.App = window.App || { Components: {} };

    var LogDataListRow = React.createClass({
        render: function () {
            if (this.props.logData.level === 'v') {
                return (
                    <div className="panel panel-success">
                        <div className="panel-heading">Verb</div>
                        <div className="panel-body">
                            <p>Time : {this.props.logData.stringTime}</p>
                            <p>Tag : {this.props.logData.tag}</p>
                            <p>Message : {this.props.logData.message}</p>
                            <p>Level : {this.props.logData.level}</p>
                        </div>
                    </div>
                );
            } else if (this.props.logData.level === 'w') {
                return (
                    <div className="panel panel-warning">
                        <div className="panel-heading">Warning</div>
                        <div className="panel-body">
                            <p>Time : {this.props.logData.stringTime}</p>
                            <p>Tag : {this.props.logData.tag}</p>
                            <p>Message : {this.props.logData.message}</p>
                            <p>Level : {this.props.logData.level}</p>
                        </div>
                    </div>
                );
            } else if (this.props.logData.level === 'i') {
                return (
                    <div className="panel panel-info">
                        <div className="panel-heading">Info</div>
                        <div className="panel-body">
                            <p>Time : {this.props.logData.stringTime}</p>
                            <p>Tag : {this.props.logData.tag}</p>
                            <p>Message : {this.props.logData.message}</p>
                            <p>Level : {this.props.logData.level}</p>
                        </div>
                    </div>
                );
            } else if (this.props.logData.level === 'd') {
                return (
                    <div className="panel panel-primary">
                        <div className="panel-heading">Debug</div>
                        <div className="panel-body">
                            <p>Time : {this.props.logData.stringTime}</p>
                            <p>Tag : {this.props.logData.tag}</p>
                            <p>Message : {this.props.logData.message}</p>
                            <p>Level : {this.props.logData.level}</p>
                        </div>
                    </div>
                );
            } else if (this.props.logData.level === 'e') {
                return (
                    <div className="panel panel-danger">
                        <div className="panel-heading">Error</div>
                        <div className="panel-body">
                            <p>Time : {this.props.logData.stringTime}</p>
                            <p>Tag : {this.props.logData.tag}</p>
                            <p>Message : {this.props.logData.message}</p>
                            <p>Level : {this.props.logData.level}</p>
                        </div>
                    </div>
                );
            }
        }
    });

    App.Components.LogDataList = React.createClass({
        render: function() {
            var i;
            var logDataNodes = [];

            for (i = 0; i < this.props.logData.length; i++) {
                logDataNodes.push(
                    <LogDataListRow logData={this.props.logData[i]} />
                );
            }

            if (logDataNodes.length == 0) {
                return (
                    <div className="logDataList">
                        <h1>데이터가 없습니다!</h1>
                    </div>
                );
            } else {
                return (
                    <div className="logDataList">
                        {logDataNodes}
                    </div>
                );
            }
        }
    });
})();