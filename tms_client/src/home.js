import React from 'react';
import Userlist from './userlist';
import Tasklist from './tasklist'
import './home.css'

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: this.props.username,
        }
    }
    render() {
        return (
            <div className='board'>
                <Userlist />
                <Tasklist />
            </div>
        );
    }
}

export default Home;