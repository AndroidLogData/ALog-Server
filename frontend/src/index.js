import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router} from 'react-router-dom'
import App from './app';
import registerServiceWorker from './registerServiceWorker';
import {AppContainer} from 'react-hot-loader';
import UserData from "./userData";

new UserData();
const rootElement = document.getElementById('root');
ReactDOM.render(
    <AppContainer>
        <Router>
            <App/>
        </Router>
    </AppContainer>, rootElement);

if (module.hot) {
    module.hot.accept('./app', () => {
        const NextApp = require('./app').default; // eslint-disable-line global-require
        ReactDOM.render(
            <AppContainer>
                <NextApp/>
            </AppContainer>,
            rootElement
        );
    });
}

registerServiceWorker();