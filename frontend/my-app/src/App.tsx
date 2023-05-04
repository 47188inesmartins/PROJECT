import React, {useEffect, useState} from 'react';
import './App.css';
import {Fetch} from "./useFetch";



function App() {
    const a = Fetch("/","GET")
    console.log(a)
      return (
        <div className="App">
            {!a.response?
                <a> hello </a>
                :
                <a> {a.response.message}</a>
            }


        </div>
      );
}

export default App;
