class App extends React.Component {
    render() {
        return (
            <div className="panel panel-success">
                <div className="panel-heading">Verb</div>
                <div className="panel-body">
                    <p>Time : {this.props.name}</p>
                    <p>Tag : {}</p>
                    <p>Message : {}</p>
                    <p>Level : {}</p>
                </div>
            </div>
        );
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));