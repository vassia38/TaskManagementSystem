import React from 'react';
import './login.css'
import './common.css'

class NameForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            elevated: undefined
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        let newState = this.state;
        newState[event.target.id] = event.target.value;
        this.setState(
            newState
        );
    }

    async handleSubmit(event) {
        event.preventDefault();
        if(this.state.username === null || this.state.username === "" ||
            this.state.password === null || this.state.password === "") {
            return;
        }
        const recipeUrl = 'http://localhost:55557/tms/login';
        const postBody = {
            username: this.state.username,
            password: this.state.password
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
        let response = await req.then(res => res);
        var data = '';
        try{
            data = await response.json();
        } catch(e) {
            alert("Login failed");
            this.setState({
                username: '',
                password: '',
                elevated: undefined
            });
            return;
        }
        this.setState({
            username: '',
            password: '',
            elevated: undefined
        });
        this.props.onLogin({
            'username': data['username'],
            'elevated': data['elevated']
        });
}

    render() {
        return (
        <form id="loginform" onSubmit={this.handleSubmit}>
            <input type="text"
                placeholder='username...'
                id="username"
                autoComplete="off"
                required
                value={this.state.username} 
                onChange={this.handleChange}
            />
            <input type="password"
                id="password"
                placeholder='password...'
                autoComplete="off"
                required
                value={this.state.password} 
                onChange={this.handleChange} 
            />
            <input type="submit" value="Login" />
        </form>
        );
    }
}

export default NameForm;