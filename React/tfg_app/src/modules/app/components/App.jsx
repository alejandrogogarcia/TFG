import Login from '../../users/components/Login';
import './App.scss'
import Body from './Body';

import Footer from './Footer';
import Header from './Header';
import Sidebar from './Sidebar';

import { Provider } from 'react-redux';

function App() {

  const isLoggedIn = true;

  return (
    <div className="App">
      <Header/>
        {isLoggedIn ?    
          <div className="flex">
            <Sidebar/>
            <Body/>
          </div>
        :
          <Login/>
        }
  </div>
  );
}

export default App;
