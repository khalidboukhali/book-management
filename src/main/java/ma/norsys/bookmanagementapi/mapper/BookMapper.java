package ma.norsys.bookmanagementapi.mapper;

import ma.norsys.bookmanagementapi.dto.requestDto.BookRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.BookResponse;
import ma.norsys.bookmanagementapi.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    BookResponse toDto(Book book);
    Book toEntity(BookRequest bookRequest);
}
