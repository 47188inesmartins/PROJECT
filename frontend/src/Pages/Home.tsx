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
import { LoggedInContextCookie } from "./Authentication/Authn";
import Cookies from 'js-cookie';


export function Home() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const role = context.role
    console.log("role = ", role)

    const hasManager = role.some((user) => user.role === "MANAGER");
    const hasEmployee = role.some((user) => user.role === "EMPLOYEE");
    const valorDoCookie = Cookies.get('name');

    console.log("valor do cookie", valorDoCookie)

    function getQueryParam(param) {
        const searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(param);
    }

    const searchValue = getQueryParam('search');
    const page = parseInt(getQueryParam('page'));
    const size = parseInt(getQueryParam('size'));

    let response;
    if (searchValue !== null) response = Fetch(`/company/search?search=${searchValue}&page=${page}&size=${size}`, 'GET');
    else response = Fetch(`/company?page=${page}&size=${size}`, 'GET');
    console.log('response = ', response);

    const goToNextPage = () => {
        window.location.href = `?page=${page+1}&size=${size}`
    };

    const goToPreviousPage = () => {
        window.location.href = `?page=${page-1}&size=${size}`
    };

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
                        <SearchBar page={page} size={size}/>
                        {response.response.content.length === 0 ? (
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
                                    <div className="pagination-container">
                                        <button
                                            className="btn btn-primary"
                                            onClick={goToPreviousPage}
                                            disabled={page <= 0}
                                        >
                                            Previous
                                        </button>
                                        <button
                                            className="btn btn-primary"
                                            onClick={goToNextPage}
                                            disabled={response.response.totalPages <= page+1}
                                        >
                                            Next
                                        </button>
                                    </div>
                                    <div>
                                        {response.response.content.map((object: any) => (
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
                                                            <u>Location: {object.address}</u>
                                                        </p>
                                                        <p>
                                                            <u>Company description:{object.description}</u>
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
            {hasManager || hasEmployee?
                <LayoutRight />
                :
                <></>
            }
        </div>
    );
}

function SearchBar(props: {page: number, size: number}) {
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
        window.location.href = `/?search=${searchTerm}&page=${props.page}&size=${props.size}`;
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
                        <div className="input-group">
                            <i className="fa fa-search"></i>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Search..."
                                value={searchTerm}
                                onChange={handleChange}
                            />
                            <div className="input-group-append">
                                <button
                                    type="button"
                                    className="btn btn-secondary dropdown-toggle"
                                    data-toggle="dropdown"
                                    aria-haspopup="true"
                                    aria-expanded="false"
                                >
                                    Distance
                                </button>
                                <div className="dropdown-menu">
                                    <a className="dropdown-item" href="#">6 KM</a>
                                    <a className="dropdown-item" href="#">12 KM</a>
                                    <a className="dropdown-item" href="#">18 KM</a>
                                </div>
                            </div>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}
