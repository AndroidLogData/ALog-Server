import React from "react";

class LogDataListRow extends React.Component {
    render() {
        if (this.props.logData.level === 'v') {
            return (
                <div className="card">
                    <div className="card-header card-success">
                        Verb
                    </div>
                    <div className="card-block">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'w') {
            return (
                <div className="card">
                    <div className="card-header card-warning">
                        Warning
                    </div>
                    <div className="card-block">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'i') {
            return (
                <div className="card">
                    <div className="card-header card-info">
                        Info
                    </div>
                    <div className="card-block">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'd') {
            return (
                <div className="card">
                    <div className="card-header card-primary">
                        Debug
                    </div>
                    <div className="card-block">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'e') {
            return (
                <div className="card">
                    <div className="card-header card-danger">
                        Error
                    </div>
                    <div className="card-block">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        }
    }
}

class LogDataList extends React.Component {
    render() {
        let i;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            logDataNodes.push(
                <div>
                    <LogDataListRow logData={this.props.logData[i]}/>
                    <br/>
                </div>
            );
        }

        if (logDataNodes.length === 0) {
            return (
                <div>
                    <h1>데이터가 없습니다!</h1>
                </div>
            );
        } else {
            return (
                <div>
                    {logDataNodes}
                </div>
            );
        }
    }
}

class LogDataBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            logData: []
        };

        this.fetchLogData();
    }

    fetchLogData() {
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
    }

    componentDidMount() {
        this.fetchLogData();
    }

    shouldComponentUpdate() {
        this.fetchLogData();
        return true;
    }

    componentWillReceiveProps() {
        this.fetchLogData();
    }

    render() {
        return (
            <div>
                <LogDataList logData={this.state.logData}/>
            </div>
        );
    }
}

export default LogDataBox;