import React from 'react';
import { createRoot } from 'react-dom/client';
import {IntlProvider} from 'react-intl';
import {initReactIntl}  from './i18n'

import 'bootstrap/dist/css/bootstrap.min.css';

import App from './modules/app/components/App';
import { Provider } from 'react-redux';
import { store } from './store/Store';

/* Configure i18n */
const {locale, messages} = initReactIntl();

const root = createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <IntlProvider locale={locale} messages={messages}>
        <App />
      </IntlProvider>
    </Provider>
  </React.StrictMode>
);