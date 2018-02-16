import React from 'react';
import FilterBar from './filterbar';
import $ from 'jquery';
import {Bar} from 'react-chartjs-2';
import queryString from "query-string";

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
        const memoryInfoOptions = {
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
        };
        const pssMemoryInfoOptions = {
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
        };

        return (
            <div>
                <div>
                    <Bar data={memoryInfo} options={memoryInfoOptions}/>
                </div>
                <div>
                    <Bar data={pssMemoryInfo} options={pssMemoryInfoOptions}/>
                </div>
            </div>
        );
    }
}

class LogDataVerb extends React.Component {
    render() {
        let i, count = 0;
        let logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            if (this.props.logData[i].level === 'v') {
                count++;
                logDataNodes.push(
                    <div className="card">
                        <div className="card-body">
                            <p>Time : {this.props.logData[i].stringTime}</p>
                            <p>Tag : {this.props.logData[i].tag}</p>
                            <p>Message : {this.props.logData[i].message}</p>
                            {(this.props.logData[i].memoryInfo == null) ? null : <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                        </div>
                    </div>
                );
            }
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
            if (this.props.logData[i].level === 'i') {
                count++;
                logDataNodes.push(
                    <div className="card">
                        <div className="card-body">
                            <p>Time : {this.props.logData[i].stringTime}</p>
                            <p>Tag : {this.props.logData[i].tag}</p>
                            <p>Message : {this.props.logData[i].message}</p>
                            {(this.props.logData[i].memoryInfo == null) ? null : <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                        </div>
                    </div>
                );
            }
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
            if (this.props.logData[i].level === 'd') {
                count++;
                logDataNodes.push(
                    <div className="card">
                        <div className="card-body">
                            <p>Time : {this.props.logData[i].stringTime}</p>
                            <p>Tag : {this.props.logData[i].tag}</p>
                            <p>Message : {this.props.logData[i].message}</p>
                            {(this.props.logData[i].memoryInfo == null) ? null : <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                        </div>
                    </div>
                );
            }
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
            if (this.props.logData[i].level === 'w') {
                count++;
                logDataNodes.push(
                    <div className="card">
                        <div className="card-body">
                            <p>Time : {this.props.logData[i].stringTime}</p>
                            <p>Tag : {this.props.logData[i].tag}</p>
                            <p>Message : {this.props.logData[i].message}</p>
                            {(this.props.logData[i].memoryInfo == null) ? null : <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                        </div>
                    </div>
                );
            }
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
            if (this.props.logData[i].level === 'e') {
                count++;
                logDataNodes.push(
                    <div className="card">
                        <div className="card-body">
                            <p>Time : {this.props.logData[i].stringTime}</p>
                            <p>Tag : {this.props.logData[i].tag}</p>
                            <p>Message : {this.props.logData[i].message}</p>
                            {(this.props.logData[i].memoryInfo == null) ? null : <LogDataMemoryChart memoryInfo={this.props.logData[i].memoryInfo}/>}
                        </div>
                    </div>
                );
            }
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
        const query = queryString.parseUrl(this.props.url);

        return (
            <div className="container-fluid">
                <div className="row">
                    <main className="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                        <FilterBar packageName={query.query.packagename}/>
                        <div className="row">
                            <LogDataVerb logData={this.state.logData}/>
                            <LogDataInfo logData={this.state.logData}/>
                            <LogDataDebug logData={this.state.logData}/>
                            <LogDataWarning logData={this.state.logData}/>
                            <LogDataError logData={this.state.logData}/>
                        </div>
                    </main>
                </div>
            </div>
        );
    }
}

export default LogData;