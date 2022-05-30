import React from 'react';
import './common.css'

class Userlist extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            onlineusers: []
        }
    }

    componentDidMount = async () => {
        const recipeUrl = 'http://localhost:55557/tms/users';
        const getHeaders = {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        }
        const requestMetadata = {
            method: 'GET',
            headers: getHeaders,
        };
        let req = fetch(recipeUrl, requestMetadata);
        let response = await req.then(res => res);
        var data = '';
        try{
            data = await response.json();
        } catch(e) {
            console.log("no one online");
        }
        let newState = this.state;
        newState['onlineusers'] = data;
        this.setState({
            newState
        });
    }

    render() {
        const items = this.state.onlineusers.map((user, index) =>
                <li key={index}>
                    {user.username}
                </li>
            );
        console.log(items)
        return(
            <div className='list-wrapper' style={{width: '20vw'}}>
                <h2>Online</h2>
                <ul>
                    {items}
                </ul>
            </div>
        );
    }
}

export default Userlist;