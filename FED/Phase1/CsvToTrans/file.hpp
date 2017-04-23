#pragma once

#include <fstream>
#include <string>

class File {

    std::string m_path;
    std::string m_name;
    std::string m_content;

public:
    typedef std::string super;

    File(std::string path);
    File(std::string path, std::string content);

    std::string getPath() const;
    std::string getName() const;
    std::string getContent() const;

    void setPath(std::string path);
    void setName(std::string path);
    void setContent(std::string content);

    void open();
};
