import * as React from 'react';
import {useState} from "react";
import '../Style/SearchBar.css'
import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBRow,
} from "mdb-react-ui-kit";
import {Fetch} from "../Utils/useFetch";
import {Layout} from "./Layout";
import {LoggedInContextCookie} from "../Authentication/Authn";

export function Search() {

    function getQueryParam(param) {
        const searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(param);
    }

// Use o valor do parâmetro 'search' aqui
    const searchValue = getQueryParam('search');
    console.log(searchValue); // Saída: "abcdefg"
    var response = null
    if (searchValue == null) {
        const response =  Fetch(`/company/search`, "GET");
    } else {
        const response = Fetch(`/company/search?search=${searchValue}`, "GET");
    }
    console.log("response = ", response)

    return (
        <div style={{ display: "flex" }}>
            <td>
                <div style={{position: "fixed",
                    top: 0,
                    left: 0,
                    bottom: 0,
                    width: "200px",
                    overflowY: "auto" }}>
                    <Layout />
                </div>
            </td>
            <td>

                <div className="list-container" style={{marginLeft: "200px",
                    overflowY: "auto"}}>
                    {!response.response ?
                        <p>Loading...</p>
                        :
                        <div>
                            <MDBContainer className="py-5">
                                <MDBCard className="px-3 pt-3"
                                         style={{ maxWidth: "100%"}} >
                                    <div>
                                        <SearchBar/>
                                        {response.response.map((object: any) => (
                                            <a key={object.id} href={`/company/${object.id}`} className="text-dark">
                                                <MDBRow className="mb-4 border-bottom pb-2">
                                                    <MDBCol size="3">
                                                        <img
                                                            src="https://mdbcdn.b-cdn.net/img/new/standard/city/041.webp"
                                                            className="img-fluid shadow-1-strong rounded"
                                                            alt="Hollywood Sign on The Hill"
                                                        />
                                                    </MDBCol>

                                                    <MDBCol size="9">
                                                        <p className="mb-2">
                                                            <strong>{object.name}</strong>
                                                        </p>
                                                        <p>
                                                            <u> {object.description}</u>
                                                        </p>
                                                    </MDBCol>
                                                </MDBRow>
                                            </a>
                                        ))}

                                    </div>
                                </MDBCard>
                            </MDBContainer>
                        </div>
                    }
                </div>
            </td>
            <td>
                <div style={{position: "fixed",
                    width: "200px",
                    overflowY: "auto",
                    alignContent: 'right'}}>
                </div>
            </td>
        </div>
    );
}

function SearchBar() {
    const [searchTerm, setSearchTerm] = useState("");

    const handleChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        //  onSearch(searchTerm);
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Search..."
                value={searchTerm}
                onChange={handleChange}
            />
            <button type="submit">Search</button>
        </form>
    );
}