import React, {useEffect, useState} from 'react';
import './App.css';

function useFetch(url: string): string | undefined {
  const [content, setContent] = useState(undefined)
  useEffect(() => {
    let cancelled = false
    async function doFetch() {
      const resp = await fetch(url)
      console.log(resp)
      const body = await resp.json()
      console.log(body)
      if (!cancelled) {
        setContent(body)
      }
    }
    setContent(undefined)
    doFetch()
    return () => {
      cancelled = true
    }
  }, [url])
  return content
}

function App() {
  const response = useFetch("http://localhost:8080/")

  console.log(response)
  return (
    <div className="App">
      <a>
        {response}
      </a>
    </div>
  );
}

export default App;
