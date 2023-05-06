import React, {useEffect, useState} from 'react';
import './App.css';
import {Fetch} from "./useFetch";
import './style.css';


function App() {
    const a = Fetch("/","GET")
    console.log(a)
      return (
          <div className="bg-image">
              {!a.response?
                  <a> hello </a>
                  :
                  <a> {a.response.message}</a>
              }

          </div>


      );
}

export default App;
