import './App.css';
import Mailbox from './component/Mailbox';
import Config from './component/Config';
import Simlist from './component/Simlist';
import Process from './component/Process';
import Subcontainer  from './component/Subcontainer';
import './component/sass/layout.scss'
import Sidebar from './component/Sidebar';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import {Routes, Route} from 'react-router-dom'
function App() {
  return (
    <div className="App" style={{ width: "100%", height: "100%" }}>
      <section>
        <div className="container">
          <div className="row">
            <div className="col-lg-2">
              <Sidebar />
            </div>
            <div className="col-lg-10">
              <Routes>
                <Route path='/' element={<Mailbox />}></Route>
                <Route path='/config' element={<Config />}></Route>
                <Route path='/simlist' element={<Simlist />}></Route> 
                <Route path='/process' element={<Process />}></Route>
              </Routes>
                {/* <Subcontainer /> */}
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default App;
