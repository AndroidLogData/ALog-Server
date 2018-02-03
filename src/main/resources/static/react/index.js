import React from 'react';
import ReactDOM from 'react-dom';
import {
    BrowserRouter as Router,
    Route,
    Link
} from 'react-router-dom'
import App from './app';
import LevelFilter from './levelfilter'

const rootElement = document.getElementById('root');
// ReactDOM.render(<App />, rootElement);

ReactDOM.render(<Router>
    <div>
        <ul>
            <li><Link to="/levelfilter/i">i</Link></li>
            <li><Link to="/levelfilter/d">d</Link></li>
            <li><Link to="/levelfilter/w">w</Link></li>
            <li><Link to="/levelfilter/v">v</Link></li>
            <li><Link to="/levelfilter/e">e</Link></li>
        </ul>
        <Route path="/logdata" component={App}/>
        <Route path="/levelfilter/:level" component={LevelFilter}/>
    </div>
</Router>, rootElement);