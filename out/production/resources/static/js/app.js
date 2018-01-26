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

ReactDOM.render(<LogDataBox url="/logdatalist"/>, document.getElementById('root'));