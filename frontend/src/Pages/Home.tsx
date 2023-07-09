import * as React from 'react';
import { useState } from 'react';
import '../Style/SearchBar.css';
import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBRow,
} from 'mdb-react-ui-kit';
import { Fetch } from '../Utils/useFetch';
import { Layout, LayoutRight } from './Layout';


export function Home() {
    function getQueryParam(param) {
        const searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(param);
    }

    const searchValue = getQueryParam('search');
    console.log(searchValue); // Saída: "abcdefg"

    var response;

    if (searchValue !== null) response = Fetch(`/company/search?search=${searchValue}`, 'GET');
    else response = Fetch('/company', 'GET');
    console.log('response = ', response);

    return (
        <div style={{ display: 'flex' }}>

            <div className="sidebar-left">
                <Layout />
            </div>

            <div
                className="list-container"
                style={{
                    flex: 1,
                    marginLeft: '200px',
                    marginRight: '200px',
                    overflowY: 'auto',
                }}
            >
                {!response.response ? (
                    <p>Loading...</p>
                ) : (
                    <div>
                        <SearchBar />
                        {response.response.length === 0 ? (
                            <MDBContainer className="py-5">
                                <MDBCard className="px-3 pt-3" style={{ maxWidth: '100%' }}>
                                    <div>
                                        <a>No matches found</a>
                                    </div>
                                    <br />
                                </MDBCard>
                            </MDBContainer>
                        ) : (
                            <MDBContainer className="py-5">
                                <MDBCard className="px-3 pt-3" style={{ maxWidth: '100%' }}>
                                    <div>

                                        {response.response.map((object: any) => (
                                            <a
                                                key={object.id}
                                                href={`/company/${object.id}`}
                                                className="text-dark"
                                            >
                                                <MDBRow className="mb-4 border-bottom pb-2">
                                                    <MDBCol size="3">
                                                        <img
                                                            src={`data:image/jpeg;base64,${object.path[0]}`}
                                                            className="img-fluid shadow-1-strong rounded"
                                                            alt="Hollywood Sign on The Hill"
                                                            style={{
                                                                width: '100%',
                                                                height: '150px',
                                                                objectFit: 'cover',
                                                            }}
                                                        />
                                                    </MDBCol>
                                                    <MDBCol size="9">
                                                        <p className="mb-2">
                                                            <strong>{object.name}</strong>
                                                        </p>
                                                        <p>
                                                            <u>{object.description}</u>
                                                        </p>
                                                    </MDBCol>
                                                </MDBRow>
                                            </a>
                                        ))}
                                    </div>
                                </MDBCard>
                            </MDBContainer>
                        )}
                    </div>
                )}
            </div>
            <LayoutRight />
        </div>
    );
}

function SearchBar() {
    const [searchTerm, setSearchTerm] = useState('');
    const [submit, setSubmit] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        setSubmit(true);
    };

    const handleChange = (e) => {
        setSearchTerm(e.target.value);
    };

    function FetchSearch() {
        window.location.href = `/?search=${searchTerm}`;
        return <></>;
    }

    return (
        <div className="container">
            <div>
                {submit ? (
                    <div>
                        <FetchSearch />
                    </div>
                ) : (
                    <form onSubmit={handleSubmit}>
                        <div>
                        <i className="fa fa-search"></i>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Search..."
                                value={searchTerm}
                                onChange={handleChange}
                            />
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}
