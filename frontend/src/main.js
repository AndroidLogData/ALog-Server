import React from 'react';
import {Link} from 'react-router-dom';
import $ from 'jquery';

class MainPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            logData: []
        };

        this.fetchPackageName();
    }

    fetchPackageName() {
        $.ajax({
            url: '/main',
            dataType: "json",
            cache: false,
            success: function (data) {
                this.setState({logData: data});
            }.bind(this),
            error: function (data) {
                console.log("error");
            }.bind(this)
        });
    }

    shouldComponentUpdate(nextProps, nextState) {
        this.fetchPackageName();
        return nextState.length !== this.state.logData.length;
    }

    render() {
        let i;
        let mainData = [];

        for (i = 0; i < this.state.logData.length; i++) {
            let max = Math.max(
                this.state.logData[i].verb,
                this.state.logData[i].info,
                this.state.logData[i].debug,
                this.state.logData[i].warning,
                this.state.logData[i].error);

            mainData.push(
                <div className="card" style={{marginRight: 0.5 + "%", marginBottom: 0.5 + "%"}}>
                    <div className="card-block">
                        <h3 className="card-title">{this.state.logData[i].packageName}</h3>
                        <p className="text-center">Log Level Information</p>
                        <p className="badge badge-success">Verb : {this.state.logData[i].verb}</p>
                        <p className="badge badge-info">Info : {this.state.logData[i].info}</p>
                        <p className="badge badge-primary">Debug : {this.state.logData[i].debug}</p>
                        <p className="badge badge-warning">Warning : {this.state.logData[i].warning}</p>
                        <p className="badge badge-danger">Error : {this.state.logData[i].error}</p>
                        <p className="card-text">최근 크래쉬 데이터
                            : {this.state.logData[i].recentCrashTime == null ? 'null' : this.state.logData[i].recentCrashTime}</p>
                        <Link to={{pathname: '/logdata/filter/packagename/' + this.state.logData[i].packageName}}
                              className="btn btn-primary" role="button">Show</Link>
                    </div>
                </div>
            );
        }

        if (mainData.length === 0) {
            return (
                <div className="container-fluid">
                    <div className="row">
                        <main className="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                            <h1>Dashboard</h1>

                            <div className="jumbotron">
                                <h1>Android Log Data Viewer</h1>
                                <hr className="my-4"/>
                                <p>안드로이드 로그 데이터와 메모리 관리를 위한 페이지</p>
                            </div>
                        </main>
                    </div>
                </div>
            );
        } else {
            return (
                <div className="container-fluid">
                    <div className="row">
                        <main className="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                            <h1>Dashboard</h1>

                            <div className="jumbotron">
                                <h1>Android Log Data Viewer</h1>
                                <hr className="my-4"/>
                                <p>안드로이드 로그 데이터와 메모리 관리를 위한 페이지</p>
                            </div>
                            <div className="row col-md text-center placeholders">
                                {mainData}
                            </div>
                        </main>
                    </div>
                </div>
            );
        }
    }
}

export default MainPage;