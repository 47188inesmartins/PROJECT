/*import * as React from 'react';
import {useState} from 'react';
import axios from 'axios';

const App = () => {
    const [data, setData] = useState({data: []});
    const [isLoading, setIsLoading] = useState(false);
    const [err, setErr] = useState('');

    const handleClick = async () => {
        setIsLoading(true);
        try {
            const {data} = await axios.get('https://reqres.in/api/users', {
                headers: {
                    Accept: 'application/json',
                },
            });

            console.log('data is: ', JSON.stringify(data, null, 4));

            setData(data);
        } catch (err) {
            setErr(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    console.log(data);

    return (
        <div>
            {err && <h2>{err}</h2>}

            <button onClick={handleClick}>Fetch data</button>

            {isLoading && <h2>Loading...</h2>}

            {data.data.map(person => {
                return (
                    <div key={person.id}>
                        <h2>{person.email}</h2>
                        <h2>{person.first_name}</h2>
                        <h2>{person.last_name}</h2>
                        <br />
                    </div>
                );
            })}
        </div>
    );
};

export default App;*/