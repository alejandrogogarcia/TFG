import React from 'react';
import ReactDOM from 'react-dom';
import {IntlProvider} from 'react-intl';

import 'bootstrap/dist/css/bootstrap.min.css';

import App from './modules/app/components/App';
import {initReactIntl}  from './i18n'

/* Configure i18n */
const {locale, messages} = initReactIntl();

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <IntlProvider locale={locale} messages={messages}>
      <App />
    </IntlProvider>
  </React.StrictMode>
);