#include "file.hpp"

File::File(std::string path) : m_path(path), m_name(path.substr(path.find_last_of('\\') + 1, path.size() - path.find_last_of('\\'))) {}

std::string File::getPath() const {
    return m_path;
}

std::string File::getName() const {
    return m_name;
}

std::string File::getContent() const {
    return m_content;
}

void File::setPath(std::string path) {
    m_path = path;
    m_name = path.substr(path.find_last_of('\\') + 1, path.size() - path.find_last_of('\\'));
}

void File::setName(std::string path) {
    m_name = path.substr(path.find_last_of('\\') + 1, path.size() - path.find_last_of('\\'));
}

void File::setContent(std::string content) {
    m_content = content;
}
