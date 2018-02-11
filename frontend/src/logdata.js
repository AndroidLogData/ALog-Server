import React from 'react';
import FilterBar from './filterbar';
import $ from 'jquery';

class LogDataListRow extends React.Component {
    render() {
        if (this.props.logData.level === 'v') {
            return (
                <div className="panel panel-success">
                    <div className="panel-heading">
                        Verb
                    </div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'w') {
            return (
                <div className="panel panel-warning">
                    <div className="panel-heading">
                        Warning
                    </div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'i') {
            return (
                <div className="panel panel-info">
                    <div className="panel-heading">
                        Info
                    </div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'd') {
            return (
                <div className="panel panel-primary">
                    <div className="panel-heading">
                        Debug
                    </div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'e') {
            return (
                <div className="panel panel-danger">
                    <div className="panel-heading">
                        Error
                    </div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
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

    shouldComponentUpdate(nextProps, nextState) {
        this.fetchLogData();
        return nextState.length !== this.state.logData.length;
    }

    render() {
        return (
            <div>
                <LogDataList logData={this.state.logData}/>
            </div>
        );
    }
}

class LogData extends React.Component {
    render() {
        return (
            <div className="container-fluid">
                <div className="row">
                    <main className="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                        <FilterBar/>
                        <LogDataBox url={this.props.url}/>
                    </main>
                </div>
            </div>
        );
    }
}

export default LogData;