import React, { useState } from "react";
import RoomPaginator from "../common/RoomPaginator";
import RoomCard from "../room/RoomCard";
import { Row, Button } from "react-bootstrap";

const RoomSearchResult = ({ results, onClearSearch }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const resultsPerPage = 3;
  const totalResults = results.length;
  const totalPages = Math.ceil(totalResults / resultsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const startIndex = (currentPage - 1) * resultsPerPage;
  const endIndex = startIndex + resultsPerPage;
  const paginatedResults = results.slice(startIndex, endIndex);

  return (
    <>
      {results.length > 0 ? (
        <>
          <h5 className="text-center mt-5">Search Results</h5>
          <Row>
            {paginatedResults.map((room) => (
              <RoomCard key={room.roomId} room={room} />
            ))}
          </Row>
          <Row className="justify-content-between mt-3">
            {totalResults > resultsPerPage && (
              <RoomPaginator
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            )}
            <Button variant="secondary" onClick={onClearSearch}>
              Clear Search
            </Button>
          </Row>
        </>
      ) : (
        <p className="text-center mt-5">No rooms found. Please adjust your search criteria.</p>
      )}
    </>
  );
};

export default RoomSearchResult;
