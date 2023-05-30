import './App.scss'
import Body from './Body';

import Footer from './Footer';
import Header from './Header';
<<<<<<< Updated upstream
import Sidebar from './Sidebar'
=======
import Sidebar from './Sidebar';
>>>>>>> Stashed changes

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
