import React from 'react';
import FilterBar from './filterbar';
import $ from 'jquery';
import {Pie} from 'react-chartjs-2';

class LogDataListRow extends React.Component {
    render() {
        if (this.props.logData.level === 'v') {
            return (
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href="#verb-logdata">Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href="#verb-memory">Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane fade in active" id="verb-logdata">
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
                            </div>
                            <div className="tab-pane fade in active" id="verb-memory">
                                {(this.props.logData.memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData.memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'w') {
            return (
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href="#warning-logdata">Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href="#warning-memory">Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane fade in active" id="warning-logdata">
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
                            </div>
                            <div className="tab-pane fade in active" id="warning-memory">
                                {(this.props.logData.memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData.memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'i') {
            return (
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href="#info-logdata">Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href="#info-memory">Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane fade in active" id="info-logdata">
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
                            </div>
                            <div className="tab-pane fade in active" id="info-memory">
                                {(this.props.logData.memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData.memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'd') {
            return (
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href="#debug-logdata">Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href="#debug-memory">Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane fade in active" id="debug-logdata">
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
                            </div>
                            <div className="tab-pane fade in active" id="debug-memory">
                                {(this.props.logData.memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData.memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'e') {
            return (
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href="#error-logdata">Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href="#error-memory">Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane fade in active" id="error-logdata">
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
                            </div>
                            <div className="tab-pane fade in active" id="error-memory">
                                {(this.props.logData.memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData.memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
    }
}

class LogDataMemoryChart extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let memoryName = [];
        let memoryValue = [];
        let pssMemoryName = [];
        let pssMemoryValue = [];
        let memoryPercentage = 0;
        let threshold = 0;
        let lowMemory = false;
        let jsonString = JSON.stringify(this.props.memoryInfo);

        JSON.parse(jsonString, (key, value) => {
            if (key === 'availMemory' || key === 'totalMemory') {
                memoryName.push(key);
                memoryValue.push(value / 1024);
            } else if (key === 'memoryPercentage') {
                memoryPercentage = value;
            } else if (key === 'lowMemory') {
                lowMemory = value;
            } else if (key === 'threshold') {
                threshold = value;
            } else if (key === 'dalvikPss' || key === 'nativePss' || key === 'totalPss' || key === 'otherPss') {
                pssMemoryName.push(key);
                pssMemoryValue.push(value);
            }
        });

        const memoryInfo = {
            labels: memoryName,
            datasets: [{
                data: memoryValue,
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56'
                ],
                hoverBackgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56'
                ]
            }]
        };

        const pssMemoryInfo = {
            labels: pssMemoryName,
            datasets: [{
                data: pssMemoryValue,
                backgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56'
                ],
                hoverBackgroundColor: [
                    '#FF6384',
                    '#36A2EB',
                    '#FFCE56'
                ]
            }]
        };

        return (
            <div className="col-sm-9 ml-sm-auto col-md-10 pt-2">
                <div className="row">
                    <div className="col-6 col-sm-5 placeholder" style={{width: 40 + '%'}}>
                        <h5 className="text-center">Memory Information(MB)</h5>
                        <Pie data={memoryInfo}/>
                    </div>
                    <div className="col-6 col-sm-5 placeholder" style={{width: 40 + '%'}}>
                        <h5 className="text-center">Proportional Set Size Information(KB)</h5>
                        <Pie data={pssMemoryInfo}/>
                    </div>
                </div>
            </div>
        );
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