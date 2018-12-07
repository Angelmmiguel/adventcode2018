#include <iostream>

int main(int argc, char* argv[]) {
  std::string line;
  int total_frequency = 0;

  while(std::getline(std::cin, line)) {
    auto num = std::stoi(line);
    total_frequency += num;
  }

  std::cout << "Total Frequency " << total_frequency << std::endl;

  return EXIT_SUCCESS;
}
