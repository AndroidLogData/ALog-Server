import React from 'react';
import FilterBar from './filterbar';
import $ from 'jquery';
import {Bar} from 'react-chartjs-2';

class LogDataListRow extends React.Component {
    render() {
        if (this.props.logData.level === 'v') {
            return (
                <div className="card" style={{marginTop: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#verb-logdata" + this.props.logDataIndex}>Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab"
                                   href={"#verb-memory" + this.props.logDataIndex}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"#verb-logdata" + this.props.logDataIndex}>
                                <div className="panel panel-success"
                                     style={{marginTop: 0.2 + "%", marginBottom: 0.2 + "%"}}>
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
                            <div className="tab-pane fade in" id={"#verb-memory" + this.props.logDataIndex}>
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
                <div className="card" style={{marginTop: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#warning-logdata" + this.props.logDataIndex}>Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab"
                                   href={"#warning-memory" + this.props.logDataIndex}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"warning-logdata" + this.props.logDataIndex}>
                                <div className="panel panel-warning"
                                     style={{marginTop: 0.2 + "%", marginBottom: 0.2 + "%"}}>
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
                            <div className="tab-pane fade in" id={"warning-memory" + this.props.logDataIndex}>
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
                <div className="card" style={{marginTop: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#info-logdata" + this.props.logDataIndex}>Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab"
                                   href={"#info-memory" + this.props.logDataIndex}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"info-logdata" + this.props.logDataIndex}>
                                <div className="panel panel-info"
                                     style={{marginTop: 0.2 + "%", marginBottom: 0.2 + "%"}}>
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
                            <div className="tab-pane fade in" id={"info-memory" + this.props.logDataIndex}>
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
                <div className="card" style={{marginTop: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#debug-logdata" + this.props.logDataIndex}>Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab"
                                   href={"#debug-memory" + this.props.logDataIndex}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"debug-logdata" + this.props.logDataIndex}>
                                <div className="panel panel-primary"
                                     style={{marginTop: 0.2 + "%", marginBottom: 0.2 + "%"}}>
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
                            <div className="tab-pane fade in" id={"debug-memory" + this.props.logDataIndex}>
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
                <div className="card" style={{marginTop: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-header">
                        <ul className="nav nav-tabs card-header-tabs">
                            <li className="nav-item">
                                <a className="nav-link active" data-toggle="tab"
                                   href={"#error-logdata" + this.props.logDataIndex}>Log Data</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" data-toggle="tab"
                                   href={"#error-memory" + this.props.logDataIndex}>Memory</a>
                            </li>
                        </ul>
                    </div>
                    <div className="card-body">
                        <div className="tab-content">
                            <div className="tab-pane active" id={"error-logdata" + this.props.logDataIndex}>
                                <div className="panel panel-danger"
                                     style={{marginTop: 0.2 + "%", marginBottom: 0.2 + "%"}}>
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
                            <div className="tab-pane fade in" id={"error-memory" + this.props.logDataIndex}>
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
        let hexColor = ['#36A2EB', '#FFCE56', '#FF6384', '#3CF0FF'];
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

        let memoryDataSets = [];
        let pssMemoryDataSets = [];

        for (let i = 0; i < memoryName.length; i++) {
            memoryDataSets.push(
                {
                    label: memoryName[i],
                    backgroundColor: hexColor[i],
                    borderColor: hexColor[i],
                    borderWidth: 1,
                    hoverBackgroundColor: hexColor[i],
                    hoverBorderColor: hexColor[i],
                    data: [memoryValue[i]]
                }
            );
        }

        for (let i = 0; i < pssMemoryName.length; i++) {
            pssMemoryDataSets.push(
                {
                    label: pssMemoryName[i],
                    backgroundColor: hexColor[i],
                    borderColor: hexColor[i],
                    borderWidth: 1,
                    hoverBackgroundColor: hexColor[i],
                    hoverBorderColor: hexColor[i],
                    data: [pssMemoryValue[i]]
                }
            );
        }


        const memoryInfo = {
            labels: memoryName,
            datasets: memoryDataSets
        };
        const pssMemoryInfo = {
            labels: pssMemoryName,
            datasets: pssMemoryDataSets
        };

        return (
            <div className="col-sm-9 ml-sm-auto col-md-10 pt-2">
                <div className="row">
                    <div style={{width: 40 + '%'}}>
                        <Bar data={memoryInfo} options={{
                            scales: {
                                xAxes: [{
                                    barPercentage: 0.3,
                                    categoryPercentage: 1.0
                                }]
                            },
                            title: {
                                display: true,
                                text: 'Memory Information(MB)'
                            }
                        }}/>
                    </div>
                    <div style={{width: 40 + '%'}}>
                        <Bar data={pssMemoryInfo} options={{
                            scales: {
                                xAxes: [{
                                    barPercentage: 0.3,
                                    categoryPercentage: 1.0
                                }]
                            },
                            title: {
                                display: true,
                                text: 'Proportional Set Size Information(KB)'
                            }
                        }}/>
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
                    <LogDataListRow logData={this.props.logData[i]} logDataIndex={i}/>
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