import React from 'react';
import ReactDOM from 'react-dom';
import {
    BrowserRouter as Router,
    Route
} from 'react-router-dom'
import App from './app';
import LevelFilter from './levelfilter'
import TagFilter from './tagfilter';
import PackageFilter from './packagefilter';
import FilterBar from './filterbar';

const rootElement = document.getElementById('root');
// ReactDOM.render(<App />, rootElement);

ReactDOM.render(<Router>
    <div>
        <FilterBar/>
        <Route path="/logdata" component={App}/>
        <Route path="/levelfilter/:level" component={LevelFilter}/>
        <Route path="/tagfilter/:tag" component={TagFilter}/>
        <Route path="/packagenamefilter/:packagename" component={PackageFilter}/>
    </div>
</Router>, rootElement);