import './App.scss'
import Body from './Body';

import Footer from './Footer';
import Header from './Header';
import Sidebar from './Sidebar'

function App() {
  return (
    <div className="App">
      <Header/>
      <div className="flex">
          <Sidebar/>
          <Body/>
      </div>
      <Footer/>
    </div>
  );
}

export default App;
