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
import {Dropdown, DropdownButton} from "react-bootstrap";


export function Home() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const role = context.role
    console.log("role = ", role)

    const [distance, setDistance] = useState<string|undefined>(undefined)

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

    let url;
    if (searchValue !== null) {

        url = `/company/search?search=${searchValue}&page=${page}&size=${size}`;
    } else {
        const getPage: number | undefined = Number.isNaN(parseInt(String(page))) ? undefined : parseInt(String(page));
        const getSize: number | undefined = Number.isNaN(parseInt(String(size))) ? undefined : parseInt(String(size));
        url = `/?page=${getPage}&size=${getSize}`;
        if (distance !== undefined) {
            url += `&distance=${distance}`;
        }
    }

    console.log("url", url)

    const response = Fetch(url, 'GET');

    console.log("response = ", response)

    const goToNextPage = () => {
        let nextPageUrl = `?page=${page+1}&size=${size}`
        if (searchValue) nextPageUrl += `&search=${searchValue}`;
        window.location.href = nextPageUrl
    };

    const goToPreviousPage = () => {
        let previousPageUrl = `?page=${page-1}&size=${size}`
        if (searchValue) previousPageUrl += `&search=${searchValue}`;
        window.location.href = previousPageUrl
    };


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
        const getPage: number | undefined = Number.isNaN(parseInt(String(page))) ? undefined : parseInt(String(page));
        const getSize: number | undefined = Number.isNaN(parseInt(String(size))) ? undefined : parseInt(String(size));
        window.location.href = `/?search=${searchTerm}&page=${getPage}&size=${getSize}`;
        return <></>;
    }

    function handleFetchDistance(value){
        setDistance(value)
    }

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
                                            <DropdownButton title="Distance" className="dropdown-button">
                                                <Dropdown.Item onClick={() => handleFetchDistance(10.0)}>10 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(20.0)}>20 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(30.0)}>30 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(40.0)}>40 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(undefined)}>Any</Dropdown.Item>
                                            </DropdownButton>
                                        </div>
                                    </form>
                                )}
                            </div>
                        </div>
                        {response.response?
                            <>
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
                                        {response.response ?
                                            <>
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
                                                ))}</> : <></>
                                        }</div>
                                </MDBCard>
                            </MDBContainer>
                        )}</>: <></>
                        }
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
