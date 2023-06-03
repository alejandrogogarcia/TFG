import Login from '../../users/components/Login';
import './App.scss'
import Body from './Body';

import Header from './Header';
import Sidebar from './Sidebar';

import { useSelector } from 'react-redux';

function App() {

  const isLoggedIn = useSelector((state) => state.user.token);

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
