#include <iostream>
#include <fstream>
#include <regex>
#include <algorithm>
#include <set>

typedef struct {
  int month;
  int day;
  int hour;
  int min;
  std::string action;
  std::string guard_id;
} entry;

int getGuardID(std::string guard_id_str) {
  return std::stoi(guard_id_str.substr(1));
}

bool sortOrder(entry a, entry b) {
  if (a.month == b.month) {
    if (a.day == b.day) {
      return a.hour * 60 + a.min < b.hour * 60 + b.min;
    } else {
      return a.day < b.day;
    }
  } else {
    return a.month < b.month;
  }
}

entry parseEntry(std::string p) {
  std::smatch match;
  std::regex rgx("^\\[\\d{4}-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})\\] (\\w+) ([#\\w ]+)");

  std::regex_search(p, match, rgx);

  entry parsedEntry;
  parsedEntry.month = std::stoi(match[1]);
  parsedEntry.day = std::stoi(match[2]);
  parsedEntry.hour = std::stoi(match[3]);
  parsedEntry.min = std::stoi(match[4]);
  parsedEntry.action = match[5];
  parsedEntry.guard_id = match[6];

  return parsedEntry;
}

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::vector<entry> entries;

  std::string line;
  while (std::getline(fs, line)) {
    auto p = parseEntry(line);
    entries.push_back(p);
  }

  fs.close();

  std::sort(entries.begin(), entries.end(), sortOrder);

  for (auto e : entries) {
    std::cout << "1518-" << e.month << "-" << e.day << " " << e.hour << ":" << e.min << " " << e.action << " " << e.guard_id << std::endl;
  }

  return EXIT_SUCCESS;
}
