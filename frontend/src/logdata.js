import React from 'react';
import FilterBar from './filterbar';
import $ from 'jquery';
import {Bar} from 'react-chartjs-2';
import queryString from 'query-string';
import moment from 'moment';
import ReactJson from 'react-json-view'

class LogDataMemoryChart extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let hexColor = ['#36A2EB', '#FFCE56', '#FF6384', '#3CF0FF'];
        let memoryMap = new Map();
        let nativeMemoryMap = new Map();
        let debugMemoryMap = new Map();
        let memoryPercentage = 0;
        let threshold = 0;
        let lowMemory = false;
        let jsonString = JSON.stringify(this.props.memoryInfo);

        JSON.parse(jsonString, (key, value) => {
            if (key === 'availMemory' || key === 'totalMemory') {
                memoryMap.set(memoryMap.size, [key, value / 1024]);
            } else if (key === 'memoryPercentage') {
                memoryPercentage = value;
            } else if (key === 'lowMemory') {
                lowMemory = value;
            } else if (key === 'threshold') {
                threshold = value;
            } else if (key === 'nativeFreeMemory' || key === 'nativeMaxMemory' || key === 'nativeTotalMemory') {
                nativeMemoryMap.set(nativeMemoryMap.size, [key, value]);
            } else if (key === 'debugNativeFree' || key === 'debugNativeAllocated' || key === 'debugNativeAvailable') {
                debugMemoryMap.set(debugMemoryMap.size, [key, value]);
            }
        });

        let memoryDataSets = [];
        let nativeMemoryDataSets = [];
        let debugMemoryDataSets = [];

        for (let i = 0; i < memoryMap.size; i++) {
            memoryDataSets.push(
                {
                    label: memoryMap.get(i)[0],
                    backgroundColor: hexColor[i],
                    borderColor: hexColor[i],
                    borderWidth: 1,
                    hoverBackgroundColor: hexColor[i],
                    hoverBorderColor: hexColor[i],
                    data: [memoryMap.get(i)[1]]
                }
            );
        }

        for (let i = 0; i < nativeMemoryMap.size; i++) {
            nativeMemoryDataSets.push(
                {
                    label: nativeMemoryMap.get(i)[0],
                    backgroundColor: hexColor[i],
                    borderColor: hexColor[i],
                    borderWidth: 1,
                    hoverBackgroundColor: hexColor[i],
                    hoverBorderColor: hexColor[i],
                    data: [nativeMemoryMap.get(i)[1]]
                }
            );
        }

        for (let i = 0; i < debugMemoryMap.size; i++) {
            debugMemoryDataSets.push(
                {
                    label: debugMemoryMap.get(i)[0],
                    backgroundColor: hexColor[i],
                    borderColor: hexColor[i],
                    borderWidth: 1,
                    hoverBackgroundColor: hexColor[i],
                    hoverBorderColor: hexColor[i],
                    data: [debugMemoryMap.get(i)[1]]
                }
            );
        }

        const memoryInfo = {
            // labels: [memoryMap.get(0)[0]],
            datasets: memoryDataSets
        };
        const nativeMemoryInfo = {
            // labels: [nativeMemoryMap.get(0)[0]],
            datasets: nativeMemoryDataSets
        };
        const debugMemoryInfo = {
            // labels: [debugMemoryMap.get(0)[0]],
            datasets: debugMemoryDataSets
        };
        const memoryInfoOptions = {
            scales: {
                xAxes: [{
                    barPercentage: 0.3,
                    categoryPercentage: 1.0
                }]
            },
            title: {
                display: true,
                text: 'Memory Information(KB)'
            }
        };
        const nativeMemoryInfoOptions = {
            scales: {
                xAxes: [{
                    barPercentage: 0.3,
                    categoryPercentage: 1.0
                }],
            },
            title: {
                display: true,
                text: 'Native Heap Memory(KB)'
            }
        };
        const debugMemoryInfoOptions = {
            scales: {
                xAxes: [{
                    barPercentage: 0.3,
                    categoryPercentage: 1.0
                }],
            },
            title: {
                display: true,
                text: 'Debug Native Heap Memory(KB)'
            }
        };

        return (
            <div>
                <button type="button" className="btn btn-primary" data-toggle="modal"
                        data-target="#memoryModal">
                    Memory Data
                </button>
                <ChartModalViewer memoryInfo={memoryInfo} memoryInfoOptions={memoryInfoOptions}
                                  nativeMemoryInfo={nativeMemoryInfo} nativeMemoryInfoOptions={nativeMemoryInfoOptions}
                                  debugMemoryInfo={debugMemoryInfo} debugMemoryInfoOptions={debugMemoryInfoOptions}/>
                <div>
                    <p>Memory Percentage : {memoryPercentage.toFixed(2)}</p>
                    <p>Low Memory : {(lowMemory) ? 'true' : 'false'}</p>
                </div>
            </div>
        );
    }
}

class ChartModalViewer extends React.Component {
    render() {
        return (
            <div className="modal fade" id="memoryModal" tabIndex="-1" role="dialog"
                 aria-labelledby="memoryModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-lg" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="memoryModalLabel">Data Memory Viewer</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="row">
                                <div className="col-md col-sm">
                                    <Bar data={this.props.memoryInfo} options={this.props.memoryInfoOptions}
                                         width={300} height={300}/>
                                </div>
                                <div className="col-md col-sm">
                                    <Bar data={this.props.nativeMemoryInfo} options={this.props.nativeMemoryInfoOptions}
                                         width={300} height={300}/>
                                </div>
                                <div className="col-md col-sm">
                                    <Bar data={this.props.debugMemoryInfo} options={this.props.debugMemoryInfoOptions}
                                         width={300} height={300}/>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

class MessageJsonModal extends React.Component {
    render() {
        return (
            <div className="modal fade" id="jsonModal" tabIndex="-1" role="dialog"
                 aria-labelledby="jsonModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="jsonModalLabel">JSON Data Viewer</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body" style={{textAlign: 'justify'}}>
                            <ReactJson src={this.props.message}/>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

class LogDataVerb extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            count++;

            logDataNodes.push(
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href={"#verb-logdata" + i}>Log</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href={"#verb-memory" + i}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"verb-logdata" + i}>
                                <p>Time : {moment(this.props.logData[i].time).format('YYYY-MM-DD HH:mm:ss.SSS')}</p>
                                <p>Tag : {this.props.logData[i].tag}</p>
                                <p>Message : {(typeof(this.props.logData[i].message) === 'object') ?
                                    <div>
                                        <button type="button" className="btn btn-primary" data-toggle="modal"
                                                data-target="#jsonModal">
                                            JSON Data
                                        </button>
                                        <MessageJsonModal message={this.props.logData[i].message}/>
                                    </div> :
                                    this.props.logData[i].message.toString()}</p>
                            </div>
                            <div className="tab-pane fade in" id={"verb-memory" + i}>
                                {(this.props.logData[i].memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div className="col-md">
                <div className="card" style={{marginBottom: 0.5 + "%"}}>
                    <div className="card-header bg-success">
                        Verb<span className="badge badge-info">{count}</span>
                    </div>
                    <div className="card-body">
                        {logDataNodes}
                    </div>
                </div>
            </div>
        );
    }
}

class LogDataInfo extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            count++;

            logDataNodes.push(
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href={"#info-logdata" + i}>Log</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href={"#info-memory" + i}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"info-logdata" + i}>
                                <p>Time : {moment(this.props.logData[i].time).format('YYYY-MM-DD HH:mm:ss.SSS')}</p>
                                <p>Tag : {this.props.logData[i].tag}</p>
                                <p>Message : {(typeof(this.props.logData[i].message) === 'object') ?
                                    <div>
                                        <button type="button" className="btn btn-primary" data-toggle="modal"
                                                data-target="#jsonModal">
                                            JSON Data
                                        </button>
                                        <MessageJsonModal message={this.props.logData[i].message}/>
                                    </div> :
                                    this.props.logData[i].message.toString()}</p>
                            </div>
                            <div className="tab-pane fade in" id={"info-memory" + i}>
                                {(this.props.logData[i].memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div className="col-md">
                <div className="card" style={{marginBottom: 0.5 + "%"}}>
                    <div className="card-header bg-light">
                        Info<span className="badge badge-info">{count}</span>
                    </div>
                    <div className="card-body">
                        {logDataNodes}
                    </div>
                </div>
            </div>
        );
    }
}

class LogDataDebug extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            count++;

            logDataNodes.push(
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href={"#debug-logdata" + i}>Log</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href={"#debug-memory" + i}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"debug-logdata" + i}>
                                <p>Time : {moment(this.props.logData[i].time).format('YYYY-MM-DD HH:mm:ss.SSS')}</p>
                                <p>Tag : {this.props.logData[i].tag}</p>
                                <p>Message : {(typeof(this.props.logData[i].message) === 'object') ?
                                    <div>
                                        <button type="button" className="btn btn-primary" data-toggle="modal"
                                                data-target="#jsonModal">
                                            JSON Data
                                        </button>
                                        <MessageJsonModal message={this.props.logData[i].message}/>
                                    </div> :
                                    this.props.logData[i].message.toString()}</p>
                            </div>
                            <div className="tab-pane fade in" id={"debug-memory" + i}>
                                {(this.props.logData[i].memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div className="col-md">
                <div className="card" style={{marginBottom: 0.5 + "%"}}>
                    <div className="card-header bg-primary">
                        Debug<span className="badge badge-info">{count}</span>
                    </div>
                    <div className="card-body">
                        {logDataNodes}
                    </div>
                </div>
            </div>
        );
    }
}

class LogDataWarning extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            count++;

            logDataNodes.push(
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#warning-logdata" + i}>Log</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href={"#warning-memory" + i}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"warning-logdata" + i}>
                                <p>Time : {moment(this.props.logData[i].time).format('YYYY-MM-DD HH:mm:ss.SSS')}</p>
                                <p>Tag : {this.props.logData[i].tag}</p>
                                <p>Message : {(typeof(this.props.logData[i].message) === 'object') ?
                                    <div>
                                        <button type="button" className="btn btn-primary" data-toggle="modal"
                                                data-target="#jsonModal">
                                            JSON Data
                                        </button>
                                        <MessageJsonModal message={this.props.logData[i].message}/>
                                    </div> :
                                    this.props.logData[i].message.toString()}</p>
                            </div>
                            <div className="tab-pane fade in" id={"warning-memory" + i}>
                                {(this.props.logData[i].memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div className="col-md">
                <div className="card" style={{marginBottom: 0.5 + "%"}}>
                    <div className="card-header bg-warning">
                        Warning<span className="badge badge-info">{count}</span>
                    </div>
                    <div className="card-body">
                        {logDataNodes}
                    </div>
                </div>
            </div>
        );
    }
}

class LogDataError extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            count++;

            logDataNodes.push(
                <div className="card">
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab" href={"#error-logdata" + i}>Log</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab" href={"#error-memory" + i}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"error-logdata" + i}>
                                <p>Time : {moment(this.props.logData[i].time).format('YYYY-MM-DD HH:mm:ss.SSS')}</p>
                                <p>Tag : {this.props.logData[i].tag}</p>
                                <p>Message : {(typeof(this.props.logData[i].message) === 'object') ?
                                    <div>
                                        <button type="button" className="btn btn-primary" data-toggle="modal"
                                                data-target="#jsonModal">
                                            JSON Data
                                        </button>
                                        <MessageJsonModal message={this.props.logData[i].message}/>
                                    </div> :
                                    this.props.logData[i].message.toString()}</p>
                            </div>
                            <div className="tab-pane fade in" id={"error-memory" + i}>
                                {(this.props.logData[i].memoryInfo == null) ?
                                    <h1>Debug Mode를 true로 해주세요</h1> :
                                    <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                            </div>
                        </div>
                    </div>
                </div>
            );
        }

        return (
            <div className="col-md">
                <div className="card" style={{marginBottom: 0.5 + "%"}}>
                    <div className="card-header bg-danger">
                        Error<span className="badge badge-info">{count}</span>
                    </div>
                    <div className="card-body">
                        {logDataNodes}
                    </div>
                </div>
            </div>
        );
    }
}

class LogData extends React.Component {
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
                this.setState({logData: data});
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
        const query = queryString.parseUrl(this.props.url);
        let logLevelVerb = [];
        let logLevelInfo = [];
        let logLevelDebug = [];
        let logLevelWarning = [];
        let logLevelError = [];

        for (let i = 0; i < this.state.logData.length; i++) {
            if (this.state.logData[i].level === 'v') {
                logLevelVerb.push(this.state.logData[i]);
            } else if (this.state.logData[i].level === 'i') {
                logLevelInfo.push(this.state.logData[i]);
            } else if (this.state.logData[i].level === 'd') {
                logLevelDebug.push(this.state.logData[i]);
            } else if (this.state.logData[i].level === 'w') {
                logLevelWarning.push(this.state.logData[i]);
            } else if (this.state.logData[i].level === 'e') {
                logLevelError.push(this.state.logData[i]);
            }
        }

        return (
            <div className="container-fluid">
                <div className="row">
                    <main className="col-sm offset-sm-3 col-md offset-md-2 pt-3">
                        <FilterBar packageName={query.query["package-name"]}/>
                        <div className="row">
                            <LogDataVerb logData={logLevelVerb}/>
                            <LogDataInfo logData={logLevelInfo}/>
                            <LogDataDebug logData={logLevelDebug}/>
                            <LogDataWarning logData={logLevelWarning}/>
                            <LogDataError logData={logLevelError}/>
                        </div>
                    </main>
                </div>
            </div>
        );
    }
}

export default LogData;