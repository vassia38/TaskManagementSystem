import React from 'react';
import ReactDOM from 'react-dom/client';
import NameForm from './login';
import Home from './home';
import './index.css'
import './common.css'

class App extends React.Component {

    handleLogin = (user) => {
        this.setState(user);
    }

    componentDidMount = () => {
        window.addEventListener("beforeunload", async (ev) => {
            ev.preventDefault();
            if(this.state == null)
                return;
            const recipeUrl = 'http://localhost:55557/tms/logout';
            const postBody = {
                username: this.state.username,
                password: '',
                elevated: this.state.elevated
            };
            const postHeaders = {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            }
            const requestMetadata = {
                method: 'POST',
                headers: postHeaders,
                body: JSON.stringify(postBody)
            };
            let req = fetch(recipeUrl, requestMetadata);
            let response = await req.then(res => res.text());
            console.log(response);
            return response;
        });
    }
    componentWillUnmount = () => {
        window.removeEventListener('beforeunload');
    }

    render() {
        return (
        <div className='content noselect'>
        {
            this.state === null
            ? <><h1 className='title'>TMS</h1><NameForm onLogin={this.handleLogin}/></>
            : <Home user={this.state.username}/>
        }
        </div>
        );
    }
}
  
// ========================================

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
  