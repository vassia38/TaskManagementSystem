import React from 'react';
import './common.css'

class Tasklist extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    componentDidMount = async () => {
        const recipeUrl = 'http://localhost:55557/tms/tasks';
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
        newState['tasks'] = data;
        this.setState({
            newState
        });
    }

    render() {
        const items = this.state.tasks.map((task, index) =>
                <li className='tasklist-item' key={index}>
                    <h3>{task.name + " " + task.status}</h3>
                    {task.description}
                </li>
            );
        console.log(items)
        return(
            <div className='list-wrapper' style={{width: '40vw'}}>
                <h2>Tasks</h2>
                <ul>
                    {items}
                </ul>
            </div>
        );
    }
}

export default Tasklist;